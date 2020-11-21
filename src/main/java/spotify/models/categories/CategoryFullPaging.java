package spotify.models.categories;

import spotify.models.paging.Paging;

public class CategoryFullPaging {
    private Paging<CategoryFull> categories;

    public Paging<CategoryFull> getCategories() {
        return categories;
    }

    public void setCategories(Paging<CategoryFull> categories) {
        this.categories = categories;
    }
}
