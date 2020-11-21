package spotify.api.interfaces;

import spotify.models.categories.CategoryFull;

public interface BrowseApi {
    CategoryFull getCategory(String categoryId, String country, String locale);
}
