package spotify.api.interfaces;

import spotify.api.enums.QueryType;
import spotify.models.search.SearchQueryResult;

import java.util.List;
import java.util.Map;

public interface SearchApi {
    SearchQueryResult searchItem(String query, List<QueryType> listOfQueryTypes, Map<String, String> options);
}
