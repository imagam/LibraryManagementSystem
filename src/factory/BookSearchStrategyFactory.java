package factory;

import enums.SearchType;
import exceptions.InvalidSearchStrategy;
import strategy.AuthorBookSearchStrategy;
import strategy.BookSearchStrategy;
import strategy.TitleBookSearchStrategy;

public class BookSearchStrategyFactory {

    public static BookSearchStrategy getBookSearchStrategy(SearchType type) throws InvalidSearchStrategy {
        return switch (type) {
            case SearchType.AUTHOR -> new AuthorBookSearchStrategy();
            case SearchType.TITLE -> new TitleBookSearchStrategy();
            default -> throw new InvalidSearchStrategy("No matching search strategy exists");
        };
    }
}
