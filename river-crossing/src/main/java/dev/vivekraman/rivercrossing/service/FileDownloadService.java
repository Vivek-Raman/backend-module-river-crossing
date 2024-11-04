package dev.vivekraman.rivercrossing.service;

import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileDownloadService {
  private final WebClient riverCrossingWebClient;

  @Value("${river-crossing.solver-script.url:}")
  private String solverScriptURL;

  @Value("${river-crossing.solver-script.file-path:}")
  private String destinationPath;

  public Mono<Boolean> refreshSolver() {
    Mono<DataBuffer> buffer = riverCrossingWebClient.get()
      .uri(solverScriptURL)
      .retrieve()
      .bodyToMono(DataBuffer.class);
    return DataBufferUtils.write(buffer, Path.of(destinationPath))
      .share()
      .thenReturn(true);
  }
}
