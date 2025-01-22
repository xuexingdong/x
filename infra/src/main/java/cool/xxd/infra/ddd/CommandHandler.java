package cool.xxd.infra.ddd;

@FunctionalInterface
public interface CommandHandler<T extends Command, R> {
    R handle(T command);
}
