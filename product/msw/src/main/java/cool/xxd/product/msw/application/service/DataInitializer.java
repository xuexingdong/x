package cool.xxd.product.msw.application.service;

import cool.xxd.product.msw.datafetcher.DropApi;
import cool.xxd.product.msw.domain.command.AddItemCommand;
import cool.xxd.product.msw.domain.command.AddMobCommand;
import cool.xxd.product.msw.domain.command.AddMobItemCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final DropApi dropApi;

    private final MobService mobService;

    private final ItemService itemService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Starting data initialization...");

        // 处理Mob数据
        log.info("Fetching mob data...");
        var mobData = dropApi.getMobData();
        log.info("Fetched {} mob records from data source", mobData.size());

        var addMobCommands = mobData.entrySet().stream()
                .map(entry -> {
                    var addMobCommand = new AddMobCommand();
                    addMobCommand.setCode(entry.getValue().get(8).replace(".img.xml", ""));
                    addMobCommand.setName(entry.getKey());
                    addMobCommand.setLevel(Integer.parseInt(entry.getValue().get(0)));
                    if (NumberUtils.isParsable(entry.getValue().get(1))) {
                        addMobCommand.setMaxHp(Integer.parseInt(entry.getValue().get(1)));
                    }
                    if (NumberUtils.isParsable(entry.getValue().get(2))) {
                        addMobCommand.setMaxMp(Integer.parseInt(entry.getValue().get(2)));
                    }
                    if (NumberUtils.isParsable(entry.getValue().get(3))) {
                        addMobCommand.setExp(Integer.parseInt(entry.getValue().get(3)));
                    }
                    if (NumberUtils.isParsable(entry.getValue().get(4))) {
                        addMobCommand.setPhysicalDefense(Integer.parseInt(entry.getValue().get(4)));
                    }
                    if (NumberUtils.isParsable(entry.getValue().get(5))) {
                        addMobCommand.setMagicalDefense(Integer.parseInt(entry.getValue().get(5)));
                    }
                    if (NumberUtils.isParsable(entry.getValue().get(6))) {
                        addMobCommand.setBaseAccuracyRequirement(Integer.parseInt(entry.getValue().get(6)));
                    }
                    addMobCommand.setAccuracyLevelPenalty(BigDecimal.ZERO);
                    return addMobCommand;
                })
                .toList();

        log.info("Processing {} mob commands for database operations", addMobCommands.size());
        mobService.addMobs(addMobCommands);
        log.info("Successfully processed all mob data");

        // 处理Item数据
        log.info("Fetching item data...");
        var itemData = dropApi.getItemData();
        log.info("Fetched {} item records from data source", itemData.size());

        var addItemCommands = itemData.entrySet().stream()
                .map(entry -> {
                    var addItemCommand = new AddItemCommand();
                    addItemCommand.setCode(entry.getKey());
                    addItemCommand.setName(entry.getValue());
                    return addItemCommand;
                })
                .toList();

        log.info("Processing {} item commands for database operations", addItemCommands.size());
        itemService.addItems(addItemCommands);
        log.info("Successfully processed all item data");

        // 处理Drop数据
        log.info("Fetching drop data...");
        var dropData = dropApi.getDropData();
        log.info("Fetched {} drop records from data source", dropData.size());

        var addMobItemCommands = dropData.entrySet().stream()
                .map(entry -> {
                    var addMobItemCommand = new AddMobItemCommand();
                    addMobItemCommand.setMobName(entry.getKey());
                    addMobItemCommand.setItemName(entry.getValue());
                    return addMobItemCommand;
                }).toList();

        log.info("Processing {} mob-item commands for database operations", addMobItemCommands.size());
        mobService.addMobItems(addMobItemCommands);
        log.info("Successfully processed all drop data");

        log.info("Data initialization completed successfully!");
    }
}