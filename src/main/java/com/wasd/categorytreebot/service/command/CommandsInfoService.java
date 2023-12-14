package com.wasd.categorytreebot.service.command;

import java.util.List;

public interface CommandsInfoService {
    void addCommandsInfo(List<String> mappings);

    List<String> getInfo();
}
