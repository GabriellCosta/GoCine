package io.gabrielcosta.gocine.util;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by gabrielcosta on 24/02/17.
 */

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

  private static int VISIBLE_THRESHOLD = 5; // The minimum amount of items to have below your current scroll position before loading more.
  private int previousTotal = 0; // The total number of items in the dataset after the last load
  private boolean loading = true; // True if we are still waiting for the last set of data to load.

  private int current_page = 1;

  private LinearLayoutManager mLinearLayoutManager;

  protected EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {
    this.mLinearLayoutManager = linearLayoutManager;
  }

  @Override
  public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
    super.onScrolled(recyclerView, dx, dy);

    int visibleItemCount = recyclerView.getChildCount();
    int totalItemCount = mLinearLayoutManager.getItemCount();
    int firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

    if (loading && totalItemCount > previousTotal) {
      isLoading(totalItemCount);
    }

    if (!loading && (totalItemCount - visibleItemCount)
        <= (firstVisibleItem + VISIBLE_THRESHOLD)) {
      endRechead();
    }
  }

  protected abstract void onLoadMore(final int currentPage);

  private void isLoading(final int totalItemCount) {
    loading = Boolean.FALSE;
    previousTotal = totalItemCount;
  }

  private void endRechead() {
    current_page++;
    onLoadMore(current_page);
    loading = Boolean.TRUE;
  }
}