package com.example.winterwonderlandapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShoppingViewModel: ViewModel() {
    private var shoppingList: ArrayList<PresentObject> = ArrayList()
    private var wishList: ArrayList<PresentObject> = ArrayList()
    private var selectedProduct: PresentObject? = null

    private var shoppingListContainer: MutableLiveData<ArrayList<PresentObject>> = MutableLiveData(shoppingList)
    private var wishListContainer: MutableLiveData<ArrayList<PresentObject>> = MutableLiveData(wishList)
    private var selectedProductContainer: MutableLiveData<PresentObject?> = MutableLiveData(selectedProduct)

    fun setShoppingList(list: ArrayList<PresentObject>) {
        this.shoppingList = list
        shoppingListContainer.value = shoppingList
    }

    fun addToWishList(newItem: PresentObject?) {
        if (newItem != null) {
            this.wishList.add(newItem)
        }
        wishListContainer.value = wishList
    }

    fun removeFromWishList(newItem: PresentObject?) {
        this.wishList.remove(newItem)
        wishListContainer.value = wishList
    }

    fun getItemStatus(newItem: PresentObject?): Boolean {
        return this.wishList.contains(newItem)
    }

    fun getShoppingList(): ArrayList<PresentObject>? {
        return this.shoppingListContainer.value
    }

    fun getWishList(): ArrayList<PresentObject>? {
        return this.wishListContainer.value
    }

    fun setSelectedProduct(product: PresentObject?) {
        this.selectedProduct = product
        selectedProductContainer.value = selectedProduct
    }

    fun getSelectedProduct(): PresentObject? {
        return this.selectedProductContainer.value
    }

}