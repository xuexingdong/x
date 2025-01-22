package cool.xxd.infra.ddd;

@FunctionalInterface
public interface CommandHandlerWithoutResult<T extends Command> {
    void handle(T command);
}
