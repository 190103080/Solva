package solva.javabackend.task.exceptions;

public class ExchangeRateNotFoundException extends RuntimeException{

    public ExchangeRateNotFoundException(String message) {
        super(message);
    }

}
