package com.renrentui.net;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import com.lidroid.xutils.http.client.multipart.HttpMultipartMode;
import com.lidroid.xutils.http.client.multipart.MultipartEntity;

public class ProgressMultipartEntity extends MultipartEntity {

	private final ProgressListener listener;

	public ProgressMultipartEntity(final ProgressListener listener
			) {
		super();
		this.listener = listener;
	}

	public ProgressMultipartEntity(final HttpMultipartMode mode,
			final ProgressListener listener) {
		super(mode);
		this.listener = listener;
	}

	public ProgressMultipartEntity(HttpMultipartMode mode,
			final String boundary, final Charset charset,
			final ProgressListener listener) {
		super(mode, boundary, charset);
		this.listener = listener;
	}

	@Override
	public void writeTo(final OutputStream outstream) throws IOException {
		super.writeTo(new CountingOutputStream(outstream, this.listener,this.getContentLength()));
	}

	public static class CountingOutputStream extends FilterOutputStream {

		private final ProgressListener listener;
		private long transferred;
		private long totalSize;

		public CountingOutputStream(final OutputStream out,
				final ProgressListener listener, long totalSize) {
			super(out);
			this.listener = listener;
			this.transferred = 0;
			this.totalSize = totalSize;
		}

		public void write(byte[] b, int off, int len) throws IOException
		{
			out.write(b, off, len);
			this.transferred += len;
			this.listener.transferred(this.transferred,this.totalSize);
		}

		public void write(int b) throws IOException {
			out.write(b);
			this.transferred++;
			this.listener.transferred(this.transferred,this.totalSize);
		}
	}

}
