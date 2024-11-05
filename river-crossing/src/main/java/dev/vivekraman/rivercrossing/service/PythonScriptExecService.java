package dev.vivekraman.rivercrossing.service;

import java.io.FileReader;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleScriptContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class PythonScriptExecService {
  @Value("${river-crossing.solver-script.file-path:}")
  private String scriptFilePath;

  public Mono<List<Map<String, String>>> run() {
    StringWriter writer = new StringWriter();
    ScriptContext context = new SimpleScriptContext();
    context.setWriter(writer);

    ScriptEngineManager manager = new ScriptEngineManager();
    ScriptEngine engine = manager.getEngineByName("python");

    try {
      engine.eval(new FileReader(scriptFilePath), context);
    } catch (Exception e) {
      e.printStackTrace();
    }

    log.warn("Output: {}", writer.toString().trim());
    return Mono.just(List.of());
  }
}
