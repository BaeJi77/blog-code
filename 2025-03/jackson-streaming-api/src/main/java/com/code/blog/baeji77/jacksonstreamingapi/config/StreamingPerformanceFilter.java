package com.code.blog.baeji77.jacksonstreamingapi.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StreamingPerformanceFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(StreamingPerformanceFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // endpoint마다 측정
        logger.info("Request URI: {}", httpRequest.getRequestURI());

        // 시작 메모리 사용량
        long startMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long startTime = System.currentTimeMillis();

        // 원래 응답을 측정 가능한 래퍼로 교체
        CountingServletResponseWrapper responseWrapper =
                new CountingServletResponseWrapper((HttpServletResponse) response);

        // 필터 체인 계속 진행
        chain.doFilter(request, responseWrapper);

        // 종료 시간 및 메모리 사용량
        long endTime = System.currentTimeMillis();
        long endMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        // 성능 지표 로깅
        logger.info("Streaming API 성능 측정:");
        logger.info("실행 시간: {} ms", (endTime - startTime));
        logger.info("메모리 사용량: {} bytes", (endMemory - startMemory));
        logger.info("전송된 데이터 크기: {} bytes", responseWrapper.getCount());
        logger.info("처리량: {} KB/s",
                responseWrapper.getCount() / (double) (endTime - startTime) * 1000 / 1024);

        chain.doFilter(request, response);
    }
}

// 응답 크기를 측정하기 위한 래퍼 클래스
class CountingServletResponseWrapper extends HttpServletResponseWrapper {
    private CountingOutputStream output;

    public CountingServletResponseWrapper(HttpServletResponse response) throws IOException {
        super(response);
        output = new CountingOutputStream(response.getOutputStream());
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return output;
    }

    public long getCount() {
        return output.getCount();
    }

    // CountingOutputStream 내부 클래스 구현
    private static class CountingOutputStream extends ServletOutputStream {
        private final ServletOutputStream outputStream;
        private long count = 0;

        public CountingOutputStream(ServletOutputStream outputStream) {
            this.outputStream = outputStream;
        }

        @Override
        public void write(int b) throws IOException {
            outputStream.write(b);
            count++;
        }

        @Override
        public void write(byte[] b) throws IOException {
            outputStream.write(b);
            count += b.length;
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            outputStream.write(b, off, len);
            count += len;
        }

        public long getCount() {
            return count;
        }

        // 기타 필요한 메소드 오버라이드
        @Override
        public boolean isReady() {
            return outputStream.isReady();
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {
            outputStream.setWriteListener(writeListener);
        }
    }
}