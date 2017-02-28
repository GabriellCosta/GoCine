package io.gabrielcosta.gocine.util;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by gabrielcosta on 24/02/17.
 */

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

  private static final int FIRST_PAGE = 1;
  private static final int VISIBLE_THRESHOLD = 5; // The minimum amount of items to have below your current scroll position before loading more.
  private static final int INITIAL_TOTAL = 0;

  private int previousTotal = INITIAL_TOTAL; // The total number of items in the dataset after the last load
  private boolean loading = true; // True if we are still waiting for the last set of data to load.

  private int currentPage = FIRST_PAGE;

  private LinearLayoutManager linearLayoutManager;

  protected EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {
    this.linearLayoutManager = linearLayoutManager;
  }

  @Override
  public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
    super.onScrolled(recyclerView, dx, dy);

    int visibleItemCount = recyclerView.getChildCount();
    int totalItemCount = linearLayoutManager.getItemCount();
    int firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

    if (loading && totalItemCount > previousTotal) {
      isLoading(totalItemCount);
    }

    if (!loading && (totalItemCount - visibleItemCount)
        <= (firstVisibleItem + VISIBLE_THRESHOLD)) {
      endRechead();
    }
  }

  public void reset() {
    previousTotal = INITIAL_TOTAL;
    currentPage = FIRST_PAGE;
  }

  protected abstract void onLoadMore(final int currentPage);

  private void isLoading(final int totalItemCount) {
    loading = Boolean.FALSE;
    previousTotal = totalItemCount;
  }

  private void endRechead() {
    currentPage++;
    onLoadMore(currentPage);
    loading = Boolean.TRUE;
  }
}