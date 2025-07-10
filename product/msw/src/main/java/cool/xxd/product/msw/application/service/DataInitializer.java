package cool.xxd.product.msw.application.service;

import cool.xxd.product.msw.datafetcher.MswDataFetcher;
import cool.xxd.product.msw.domain.command.AddMobCommand;
import cool.xxd.product.msw.domain.command.AddMobItemCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final MswDataFetcher mswDataFetcher;

    private final MobService mobService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        var mobData = mswDataFetcher.getMobData();
        var addMobCommands = mobData.entrySet().stream()
                .map(entry -> {
                    var addMobCommand = new AddMobCommand();
                    addMobCommand.setCode(entry.getValue().get(7).replace(".img.xml", ""));
                    addMobCommand.setName(entry.getKey());
                    addMobCommand.setLevel(Integer.parseInt(entry.getValue().get(0)));
                    addMobCommand.setMaxHp(Integer.parseInt(entry.getValue().get(1)));
                    addMobCommand.setMaxMp(Integer.parseInt(entry.getValue().get(2)));
                    addMobCommand.setExp(Integer.parseInt(entry.getValue().get(3)));
                    addMobCommand.setPhysicalDefense(Integer.parseInt(entry.getValue().get(4)));
                    addMobCommand.setMagicalDefense(Integer.parseInt(entry.getValue().get(5)));
                    addMobCommand.setBaseAccuracyRequirement(Integer.parseInt(entry.getValue().get(6)));
                    addMobCommand.setAccuracyLevelPenalty(BigDecimal.ZERO);
                    return addMobCommand;
                }).toList();
        mobService.addMobs(addMobCommands);

        var itemData = mswDataFetcher.getItemData();

        var dropData = mswDataFetcher.getDropData();
        var addMobItemCommands = dropData.entrySet().stream()
                .map(entry -> {
                    var addMobItemCommand = new AddMobItemCommand();
                    addMobItemCommand.setMobName(entry.getKey());
                    addMobItemCommand.setItemName(entry.getValue());
                    return addMobItemCommand;
                }).toList();
        mobService.addMobItems(addMobItemCommands);
        System.out.println(dropData);
    }
}
