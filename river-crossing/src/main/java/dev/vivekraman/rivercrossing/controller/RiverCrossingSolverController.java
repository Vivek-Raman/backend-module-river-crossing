package dev.vivekraman.rivercrossing.controller;

import dev.vivekraman.rivercrossing.config.Constants;
import dev.vivekraman.rivercrossing.config.GameMode;
import dev.vivekraman.rivercrossing.service.FileDownloadService;
import dev.vivekraman.rivercrossing.service.PythonScriptExecService;
import dev.vivekraman.monolith.annotation.MonolithController;
import dev.vivekraman.monolith.model.Response;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@MonolithController(moduleName = Constants.MODULE_NAME)
@RequiredArgsConstructor
public class RiverCrossingSolverController {
  private final FileDownloadService fileDownloadService;
  private final PythonScriptExecService scriptExecService;
  private final Scheduler scheduler;

  @PostMapping("/update-script")
  public Mono<Response<Boolean>> refreshSolver() {
    return fileDownloadService.refreshSolver()
      .map(Response::of)
      .subscribeOn(scheduler);
  }

  @PostMapping("/solve")
  public Mono<Map<String, Object>> solve(@RequestParam GameMode gameMode) {
    return scriptExecService.solve(gameMode)
      // .map(res -> ResponseList.builder().data(res).build())
      .subscribeOn(scheduler);
  }
}
