package cool.xxd.product.msw.application.service;

import cool.xxd.product.msw.datafetcher.MswDataFetcher;
import cool.xxd.product.msw.domain.command.AddItemCommand;
import cool.xxd.product.msw.domain.command.AddMobCommand;
import cool.xxd.product.msw.domain.command.AddMobItemCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final MswDataFetcher mswDataFetcher;

    private final MobService mobService;
    private final ItemService itemService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        var mobData = mswDataFetcher.getMobData();
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
        mobService.addMobs(addMobCommands);

        var itemData = mswDataFetcher.getItemData();
        var addItemCommands = itemData.entrySet().stream()
                .map(entry -> {
                    var addItemCommand = new AddItemCommand();
                    addItemCommand.setCode(entry.getKey());
                    addItemCommand.setName(entry.getValue());
                    return addItemCommand;
                })
                .toList();
        itemService.addItems(addItemCommands);

        var dropData = mswDataFetcher.getDropData();
        var addMobItemCommands = dropData.entrySet().stream()
                .map(entry -> {
                    var addMobItemCommand = new AddMobItemCommand();
                    addMobItemCommand.setMobName(entry.getKey());
                    addMobItemCommand.setItemName(entry.getValue());
                    return addMobItemCommand;
                }).toList();
        mobService.addMobItems(addMobItemCommands);
    }
}
