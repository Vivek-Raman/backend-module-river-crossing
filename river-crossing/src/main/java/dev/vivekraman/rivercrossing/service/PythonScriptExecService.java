package dev.vivekraman.rivercrossing.service;

import java.io.FileReader;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleScriptContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.vivekraman.rivercrossing.config.GameMode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class PythonScriptExecService {
  private final ObjectMapper objectMapper;

  @Value("${river-crossing.mnc.solver-script.file-path:}")
  private String scriptMnCFilePath;

  @Value("${river-crossing.jh.solver-script.file-path:}")
  private String scriptJHFilePath; // TODO:

  public Mono<Map<String, Object>> solve(GameMode gameMode) {
    StringWriter writer = new StringWriter();
    ScriptContext context = new SimpleScriptContext();
    context.setWriter(writer);

    ScriptEngineManager manager = new ScriptEngineManager();
    ScriptEngine engine = manager.getEngineByName("python");

    String scriptFilePath = gameMode == GameMode.MnC ?
      scriptMnCFilePath :
      scriptJHFilePath;
    try {
      engine.eval(new FileReader(scriptFilePath), context);
    } catch (Exception e) {
      log.error("Execution failed: ", e);
      throw new RuntimeException(e);
    }

    String output = writer.toString().trim();
    Map<String, Object> map = null;
    try {
      map = objectMapper.readValue(output,
      objectMapper.getTypeFactory().constructMapType(
        LinkedHashMap.class, String.class, Object.class));
    } catch (Exception e) {
      log.error("Failed to read: {}, error: ", output, e);
      return Mono.just(Map.of());
    }

    log.warn("Output: {}", map);
    return Mono.just(map);
  }
}
