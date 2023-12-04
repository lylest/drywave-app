package com.example.funs.screens.neworder

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import androidx.navigation.NavController
import com.example.funs.screens.home.CleaningService
import com.example.funs.screens.home.HomeService
import com.example.funs.screens.home.SampleCleaningServiceResponse
import com.example.funs.screens.vieworder.OrderDetails
import com.example.funs.utils.DataStoreManager
import com.example.funs.utils.Utils
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class NewOrderViewModel(application: Application ) : AndroidViewModel(application) {
    private val dataStoreManager: DataStoreManager = DataStoreManager(application)
    var message = mutableStateOf("")
    var showToast = mutableStateOf(false)
    var sampleShops = mutableListOf(Shop())
    var selectedShop = mutableStateOf("empty")
    var servicesList = MutableLiveData<MutableList<CleaningService>>()
    var selectedService = mutableStateOf("empty")
    var serviceCode = mutableStateOf("empty")
    var itemsList = MutableLiveData<MutableList<Item>>()
    var pieces = MutableLiveData<MutableList<Piece>>()
    var isVisible = mutableStateOf(false)
    val activeItem = mutableStateOf<Item?>(null)
    val selectedColor = mutableStateOf<Color?>(null)
    val description = mutableStateOf("")
    val total = mutableStateOf(0)
    val quantity = mutableStateOf(0)
    val isLoading = mutableStateOf(false)
    val orderId = mutableStateOf("empty")


    fun incrementQuantity(piece: Piece) {
        val currentPieces = pieces.value ?: mutableListOf()
        val newPieces = currentPieces.map { item ->
            if (item.category == piece.category) {
                item.copy(
                    quantity = item.quantity + 1,
                    pieces = item.pieces + (item.categoryPieces * 1),
                    itemTotal = (item.quantity + 1) * item.unitPrice
                )
            } else {
                item
            }
        }.toMutableList()
        pieces.value = newPieces
        calcTotalAndQuantity(pieces)
    }

    fun decrementQuantity(piece: Piece) {
        val currentPieces = pieces.value ?: mutableListOf()

        val newPieces = currentPieces.map { item ->
            if (item.category == piece.category) {
                if (item.quantity <= 1) {
                    null
                } else {
                    item.copy(
                        quantity = item.quantity - 1,
                        pieces = item.pieces - item.categoryPieces,
                        itemTotal = (item.quantity - 1) * item.unitPrice
                    )
                }
            } else {
                item
            }
        }.filterNotNull().toMutableList()

        pieces.value = newPieces
        calcTotalAndQuantity(pieces)
    }

    fun addDescription(piece: Item) {
        isVisible.value = true
        activeItem.value = piece
    }

    fun generateUniqueId(prefix: String): String {
        val timestamp = System.currentTimeMillis()
        val uniqueId = UUID.randomUUID().toString().replace("-", "")
        return "$prefix-$timestamp-$uniqueId"
    }

    fun addPiece() {
        showToast.value = false; message.value = ""
        if (description.value.isEmpty() || description.value == "" || selectedColor.value == null) {
            showToast.value = true; message.value = "Add Description and pick color"
        } else {
            val currentItems = pieces.value ?: mutableListOf()
            activeItem.value?.let {
                currentItems.add(
                    Piece(
                        id = generateUniqueId("pieceId"),
                        category = it._id,
                        categoryPieces = it.pieces,
                        color = selectedColor.value!!,
                        description = description.value,
                        itemTotal = it.price,
                        name = it.item,
                        pieces = it.pieces,
                        quantity = 1,
                        unitPrice = it.price
                    )
                )
            }
            pieces.value = currentItems
            calcTotalAndQuantity(pieces)

            selectedColor.value = null
            activeItem.value = null
            isVisible.value = false
        }
    }

     fun calcTotalAndQuantity(items: MutableLiveData<MutableList<Piece>>) {

        var totalPrice = 0
        var totalPieces = 0

       if(items.value !== null) {
           for (item in items.value!!) {
               totalPrice += item.itemTotal
               totalPieces += item.pieces
           }
           total.value = totalPrice
           quantity.value = totalPieces
       }

    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val currentDate = Date()
        return dateFormat.format(currentDate)
    }

    fun generateCodeSet(): String {
        val randNumbers = Random.nextInt(1, 10001)
        val dateFormat = SimpleDateFormat("EEE", Locale.getDefault())
        val dayOfTheWeek = dateFormat.format(Date())

        return "${quantity.value}PC-${randNumbers}-${dayOfTheWeek.uppercase(Locale.ROOT)}-${serviceCode.value}"

    }

    fun getSampleShops(customerToken: String) {
        showToast.value = false
        message.value = ""

        viewModelScope.launch {
            val newOrderService = NewOrderService.getInstance()

            try {
                val response = newOrderService.getShops(
                    customerToken, Utils.contentType
                )

                if (response.isSuccessful) {
                    message.value = response.body()?.message.toString()
                    response.body()?.data.also {
                        if (it != null) {
                            sampleShops = it as MutableList<Shop>
                        }
                    }

                } else {
                    val error = response.errorBody()?.let {
                        Gson().fromJson(it.string(), ShopsResponse::class.java)
                    }
                    showToast.value = true
                    message.value = error?.message.toString()
                }
            } catch (e: Exception) {
                message.value = e.message.toString()
                showToast.value = true
            }
        }
    }

    fun searchShops(customerToken: String, keyword: String) {
        showToast.value = false
        message.value = ""

        viewModelScope.launch {
            val newOrderService = NewOrderService.getInstance()

            try {
                val response = newOrderService.searchShop(
                    keyword, customerToken, Utils.contentType
                )

                if (response.isSuccessful) {
                    message.value = response.body()?.message.toString()
                    response.body()?.data.also {
                        if (it != null) {
                            sampleShops = it as MutableList<Shop>
                        }
                    }

                } else {
                    val error = response.errorBody()?.let {
                        Gson().fromJson(it.string(), ShopsResponse::class.java)
                    }
                    showToast.value = true
                    message.value = error?.message.toString()
                }
            } catch (e: Exception) {
                message.value = e.message.toString()
                showToast.value = true
            }
        }
    }

    fun selectShop(clickedShop: String) {
        viewModelScope.launch {
            selectedShop.value = clickedShop
        }
    }

    fun selectService(clickedService: String,  code: String ) {
        viewModelScope.launch {
            selectedService.value = clickedService
            serviceCode.value = code
        }
    }

    fun getShopServices(customerToken: String, shopId: String) {
        showToast.value = false
        message.value = ""

        viewModelScope.launch {
            val homeService = HomeService.getInstance()

            try {
                val response = homeService.getShopServices(
                    selectedShop.value, customerToken, Utils.contentType
                )

                if (response.isSuccessful) {
                    response.body()?.data?.let {
                        servicesList.value = it
                    }

                } else {
                    val error = response.errorBody()?.let {
                        Gson().fromJson(it.string(), SampleCleaningServiceResponse::class.java)
                    }
                    message.value = error?.message.toString()
                }
            } catch (e: Exception) {
                message.value = e.message.toString()
                showToast.value = true
            }
        }
    }

    fun searchItems(customerToken: String, keyword: String) {
        showToast.value = false
        message.value = ""

        viewModelScope.launch {
            val newOrderService = NewOrderService.getInstance()

            try {
                val response = newOrderService.searchItem(
                    SearchItemBody(shopId = selectedShop.value, query = keyword), customerToken, Utils.contentType
                )

                if (response.isSuccessful) {
                    message.value = response.body()?.message.toString()
                    response.body()?.data?.let {
                        itemsList.value = it
                    }

                } else {
                    val error = response.errorBody()?.let {
                        Gson().fromJson(it.string(), ShopsResponse::class.java)
                    }
                    showToast.value = true
                    message.value = error?.message.toString()
                }
            } catch (e: Exception) {
                message.value = e.message.toString()
                showToast.value = true
            }
        }
    }

    fun saveOrder(customerToken: String, customerId: String) {
        showToast.value = false
        message.value = ""
        isLoading.value = true

        viewModelScope.launch {
            val newOrder = NewOrderService.getInstance()

            try {
                val response = newOrder.createOrder(
                    Order(
                        customer = customerId,
                        shop = selectedShop.value,
                        service = selectedService.value,
                        pieces = pieces.value!!,
                        totalCost = total.value,
                        discount = 0,
                        amountPaid = 0,
                        dateReceived = getCurrentDate(),
                        readyDate = getCurrentDate(),
                        paymentStatus = "not-paid",
                        paymentMethod = "Cash",
                        trackingId = generateCodeSet(),
                        checkoutCode = generateCodeSet(),
                        isExpress = false,
                        totalPieces = quantity.value,
                        orderStatus = "waiting-confirmation"
                    ),
                    customerToken, Utils.contentType
                )

                if (response.isSuccessful) {
                    message.value = response.body()?.message.toString()
                    showToast.value = true
                    isLoading.value = false
                    orderId.value = response.body()?.data?._id.toString()
                } else {
                    val error = response.errorBody()?.let {
                        Gson().fromJson(it.string(), ShopsResponse::class.java)
                    }
                    showToast.value = true
                    isLoading.value = false
                    message.value = error?.message.toString()
                }
            } catch (e: Exception) {
                message.value = e.message.toString()
                showToast.value = true
                isLoading.value = false
            }
        }
    }

    fun updateOrder(orderId:String, customerToken: String, customerId: String, order:OrderDetails) {
        showToast.value = false
        message.value = ""
        isLoading.value = true

        viewModelScope.launch {
            val updateOrder = NewOrderService.getInstance()
            val newTrackingId = order.trackingId.replaceFirstChar { quantity.value.toString() }
            val newCheckOutCode = order.trackingId.replaceFirstChar { quantity.value.toString()  }

            try {
                val response =updateOrder.updateOrder(
                    orderId,
                    Order(
                        customer = customerId,
                        shop = selectedShop.value,
                        service = selectedService.value,
                        pieces = pieces.value!!,
                        totalCost = total.value,
                        discount = order.discount,
                        amountPaid = order.amountPaid,
                        dateReceived = order.dateReceived,
                        readyDate = order.readyDate,
                        paymentStatus = order.paymentStatus,
                        paymentMethod = order.paymentMethod,
                        trackingId = newTrackingId,
                        checkoutCode = newCheckOutCode,
                        isExpress = order.isExpress,
                        totalPieces = quantity.value,
                        orderStatus = order.orderStatus
                    ),
                    customerToken, Utils.contentType
                )

                if (response.isSuccessful) {
                    message.value = response.body()?.message.toString()
                    showToast.value = true
                    isLoading.value = false

                } else {
                    val error = response.errorBody()?.let {
                        Gson().fromJson(it.string(), NewOrderResponse::class.java)
                    }
                    showToast.value = true
                    isLoading.value = false
                    message.value = error?.message.toString()
                }
            } catch (e: Exception) {
                message.value = e.message.toString()
                showToast.value = true
                isLoading.value = false
            }
        }
    }

}