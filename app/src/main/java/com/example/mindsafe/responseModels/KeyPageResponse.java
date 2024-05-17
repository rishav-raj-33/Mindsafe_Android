package com.example.mindsafe.responseModels;

import java.util.List;

public class KeyPageResponse {

    public KeyPageResponse() {
    }

    public KeyPageResponse(List<KeyVaultResponseModel> content, int pageNumber, int pageSize, long totalElemnets, int totalPages, boolean isLastPage) {
        this.content = content;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElemnets = totalElemnets;
        this.totalPages = totalPages;
        this.isLastPage = isLastPage;
    }

    public List<KeyVaultResponseModel> getContent() {
        return content;
    }

    public void setContent(List<KeyVaultResponseModel> content) {
        this.content = content;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalElemnets() {
        return totalElemnets;
    }

    public void setTotalElemnets(long totalElemnets) {
        this.totalElemnets = totalElemnets;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    private List<KeyVaultResponseModel> content;
    private int pageNumber;
    private int pageSize;
    private long totalElemnets;
    private int totalPages;
    private boolean isLastPage;
}
