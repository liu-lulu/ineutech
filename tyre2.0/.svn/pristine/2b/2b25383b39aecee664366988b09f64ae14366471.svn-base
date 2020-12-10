package com.psylife.util.page;

import java.util.ArrayList;
import java.util.List;

public class ListInfo<T> {
	/**
	 * 当前页
	 */
	private int currentPageNO;

	/**
	 * 最大页码
	 */
	private int maxPageNO;

	/**
	 * 第一条记录
	 */
	private int first;
	/**
	 * 最大记录数
	 */
	private int max;

	/**
	 * 所有记录数
	 */
	private int sizeOfTotalList;

	/**
	 * 记录列表
	 */
	private List<T> currentList;

	/**
	 * 页码列表
	 */
	private List<Integer> pages;
	/**
	 * 页码列表
	 */
	private List<Integer> pagesBar;


	public ListInfo(int currentPageNO, int max) {
		this.currentPageNO = currentPageNO;
		this.max = max;
		range(currentPageNO, max);
	}

	public List<T> getCurrentList() {
		return currentList;
	}

	public void setCurrentList(List<T> currentList) {
		this.currentList = currentList;
	}

	public int getCurrentPageNO() {
		return currentPageNO;
	}

	public void setCurrPageNO(int currPageNO) {
		this.currentPageNO = currPageNO;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getMaxPageNO() {
		return maxPageNO;
	}

	public void setMaxPageNO(int maxPageNO) {
		this.maxPageNO = maxPageNO;
	}

	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public void range(int pCurrPageNO, int pSizeOfPrePageList) {
		if (pCurrPageNO == 1)
			this.setFirst(0);
		else
			this.setFirst((pCurrPageNO - 1) * pSizeOfPrePageList);

		this.setMax(pSizeOfPrePageList);
	}

	public int getSizeOfTotalList() {
		return sizeOfTotalList;
	}

	public void setSizeOfTotalList(int sizeOfTotalList) {
		if ((sizeOfTotalList % max) == 0) {
			maxPageNO = sizeOfTotalList / max;
		} else {
			maxPageNO = sizeOfTotalList / max + 1;
		}
		this.sizeOfTotalList = sizeOfTotalList;
		setPages();
		setPagesBar();
	}

	/**
	 * 设置页码列表
	 */
	public void setPages() {
		pages = new ArrayList<Integer>();

		for (int i = 0; i < 10; i++) {
			int np = 0;
			if (currentPageNO - 5 > 1)
				np = currentPageNO - 5 + i;
			else
				np = i + 1;
			if (np > maxPageNO)
				break;
			pages.add(np);
		}
	}

	public List<Integer> getPages() {
		return pages;
	}

	public List<Integer> getPagesBar() {
		return pagesBar;
	}
/**
 * 分页信息，0表示……
 */
	public void setPagesBar() {
		pagesBar = new ArrayList<Integer>();
		for(int i=1;i<10&&i<=maxPageNO;i++){
			if(maxPageNO>=10){
				if(currentPageNO>=6){
					if(maxPageNO-currentPageNO<=4){
						if(i<2){
							pagesBar.add(i);
						}else{
							if(i==2){
								pagesBar.add(0);
							}else{
								pagesBar.add(maxPageNO-(9-i));
							}
						}
					}else{
						pagesBar.add(1);
						pagesBar.add(0);
						pagesBar.add(currentPageNO-2);
						pagesBar.add(currentPageNO-1);
						pagesBar.add(currentPageNO);
						pagesBar.add(currentPageNO+1);
						pagesBar.add(currentPageNO+2);
						pagesBar.add(0);
						pagesBar.add(maxPageNO);
						break;
					}
				}else{
					if(i<7){
						pagesBar.add(i);	
					}else{
						pagesBar.add(i);
						pagesBar.add(0);
						pagesBar.add(maxPageNO);
						break;
					}
					
				}
			}else{
				pagesBar.add(i);
			}
		}
	}

}
