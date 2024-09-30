package com.emazon.cart.domain.models;

import java.util.List;

public class Paginated<T>{
    private List<T> data;
    private Long currentPage;
    private Long totalItems;
    private Long totalPages;

    public Paginated(List<T> data, Long currentPage, Long totalItems, Long totalPages) {
        this.data = data;
        this.currentPage = currentPage;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }
    public Paginated() {
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Long currentPage) {
        this.currentPage = currentPage;
    }

    public Long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Long totalItems) {
        this.totalItems = totalItems;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }
}
