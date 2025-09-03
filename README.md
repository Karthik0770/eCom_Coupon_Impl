# ECom_Coupon_Impl

## CASES IMPLEMENTED -

#### DB used - 
H2 In memory database with MySQL format


### - POST /product/add : 
- Create a new product entry : Creating a product is required before adding it to a coupon's constraints or having it in cart object.

#### Request - 
```json
{
  "productName": "product name 1",
  "price": 500
}
```
#### Response -
```json
{
  "productId": 1,
  "productName": "product name 1",
  "price": 500
}
```

### - GET /v1/coupons : Get all coupons - 
- Gives all the coupons created in a list.

Sample response :
```json
{
  "status": (String) | "SUCCESS" / "FAILURE",
  "message": (String) | message to be displayed,
  "payload": [
    {
      "couponId": (Int) | Id of coupon created,
      "name": (String) | name of the coupon,
      "code": (String) | coupon code,
      "type": (ENUM) | "CART_WISE"/"PRODUCT_WISE"/"BXGY",
      "thresholdAmount": (Int) | threshold amount for the total value of the cart,
      "discountType": (ENUM) | "FLAT"/"PERCENTAGE",
      "discountAmount": (Int) | discount amount to be provided (according to the discount type provided),
      "repetitionLimit": (Int) | the number of times a BXGY coupon can be stacked,
      "startDate": (Date - dd-MM-yyyy) | start date for coupon,
      "endDate": (Date - dd-MM-yyyy) | end date for coupon
    },...
  ]
}
```

### - GET /v1/coupons/{id} : Get coupon by id -
- Fetch the coupon having the given ID.

Sample response :
```json
{
  "status": (String) | "SUCCESS" / "FAILURE",
  "message": (String) | message to be displayed,
  "payload": {
      "couponId": (Int) | Id of coupon created,
      "name": (String) | name of the coupon,
      "code": (String) | coupon code,
      "type": (ENUM) | "CART_WISE"/"PRODUCT_WISE"/"BXGY",
      "thresholdAmount": (Int) | threshold amount for the total value of the cart,
      "discountType": (ENUM) | "FLAT"/"PERCENTAGE",
      "discountAmount": (Int) | discount amount to be provided (according to the discount type provided),
      "repetitionLimit": (Int) | the number of times a BXGY coupon can be stacked,
      "startDate": (Date - dd-MM-yyyy) | start date for coupon,
      "endDate": (Date - dd-MM-yyyy) | end date for coupon
  }
}
```

### - POST /v1/coupons : Create a new coupon -

- CREATING NEW CART WISE COUPON

```json
{
  "name": (String) | name of the coupon,
  "code": (String) | coupon code,
  "type": (ENUM) | "CART_WISE"/"PRODUCT_WISE"/"BXGY",
  "details": {
    "thresholdAmount": (Int) | threshold amount for the total value of the cart,
	"discountType": (ENUM) | "FLAT"/"PERCENTAGE",
	"discountAmount": (Int) | discount amount to be provided (according to the discount type provided),
  },
  "startDate": (Date - dd-MM-yyyy) | start date for coupon,
  "endDate": (Date - dd-MM-yyyy) | end date for coupon
}
```
- CREATING NEW PRODUCT WISE COUPON

```json
{
  "name": (String) | name of the coupon,
  "code": (String) | coupon code,
  "type": (ENUM) | "CART_WISE"/"PRODUCT_WISE"/"BXGY",
  "details": {
    "products": (List<Int>) | List of product ids this coupon applies to,
	"discountType": (ENUM) | "FLAT"/"PERCENTAGE",
	"discountAmount": (Int) | discount amount to be provided (according to the discount type provided),
  },
  "startDate": (Date - dd-MM-yyyy) | start date for coupon,
  "endDate": (Date - dd-MM-yyyy) | end date for coupon
}
```

- CREATING NEW BXGY COUPON

```json
{
  "name": (String) | name of the coupon,
  "code": (String) | coupon code,
  "type": (ENUM) | "CART_WISE"/"PRODUCT_WISE"/"BXGY",
  "details": {
    "xproducts":[
      {
        "productId": (Int) | id of the product to be considered for combo,
        "quantity": (Int) | quantity of the mentioned product to be present in the cart for coupon to be valid
      },...
    ],
    "yproducts":[
      {
        "productId": (Int) | id of the product to be considered as reward for combo,
        "quantity": (Int) | quantity of the mentioned product
      },...
    ],
	"repetitionLimit": (Int) | Number of times this coupon can be stacked on the cart items,
  },
  "startDate": (Date - dd-MM-yyyy) | start date for coupon,
  "endDate": (Date - dd-MM-yyyy) | end date for coupon
}
```

### - PUT /v1/coupons/{id} : Update coupon of a given ID -

- Request same as creating new coupon with all fields as optional (Only provide the ones to be updated), except for the field "type" (Type is a mandatory field).

### - DELETE /v1/coupons/{id} : Delete coupon of a given ID -

- Delete a coupon of a given ID alon with all its relations.

```json
{
  "status": (String) | "SUCCESS" / "FAILURE",
  "message": (String) | message to be displayed,
  "payload": {
      "couponId": (Int) | Id of coupon created,
      "name": (String) | name of the coupon,
      "code": (String) | coupon code,
      "type": (ENUM) | "CART_WISE"/"PRODUCT_WISE"/"BXGY",
      "thresholdAmount": (Int) | threshold amount for the total value of the cart,
      "discountType": (ENUM) | "FLAT"/"PERCENTAGE",
      "discountAmount": (Int) | discount amount to be provided (according to the discount type provided),
      "repetitionLimit": (Int) | the number of times a BXGY coupon can be stacked,
      "startDate": (Date - dd-MM-yyyy) | start date for coupon,
      "endDate": (Date - dd-MM-yyyy) | end date for coupon
  }
}
```

### - POST /v1/applicable-coupons : Get all the applicable coupons 

- Provide cart object and get the applicable coupons according to the given cart request.
- Applicable coupons are fetched if the conditions of the coupon are satisfied by the cart items and if the current date satisfies the constraints of start date and end date for the coupon.
- Response contains the applicable coupons sorted according to the discount amount (Highest discount coupon is visible first).
#### Request
```json
{
  "cartProducts": [
    {
      "productId": (Int) | id of product,
      "quantity": (Int) | quantity of mentioned product present in cart
    },...
  ]
}
```
#### Response
```json
{
  "status": "SUCCESS",
  "message": "Applicable coupons fetched!",
  "payload": [
    {
      "couponId": (Int) | id of the applicable coupon,
      "code": (String) | coupon code ,
      "type": (ENUM) | "CART_WISE"/"PRODUCT_WISE"/"BXGY",      
      "originalCartAmount": (Int) | cart total value without any discounts,
      "discountAmount": (Int) | discount calculated,
      "finalCartAmount": (Int) | final cart amount after applying the discount,
      "message": (String) | customised message according to coupon type and discount amount
    },...
  ]
}
```

### - POST /v1/apply-coupon/{couponId} : Apply coupon on cart -


- Provide cart object and get the applicable coupons according to the given cart request.
- Applicable coupons are fetched if the conditions of the coupon are satisfied by the cart items and if the current date satisfies the constraints of start date and end date for the coupon.
#### Request
```json
{
  "cartProducts": [
    {
      "productId": (Int) | id of product,
      "quantity": (Int) | quantity of mentioned product present in cart
    },...
  ]
}
```
#### Response
```json
{
  "status": "SUCCESS",
  "message": "Applicable coupons fetched!",
  "payload": {
      "couponId": (Int) | id of the applicable coupon,
      "code": (String) | coupon code ,
      "type": (ENUM) | "CART_WISE"/"PRODUCT_WISE"/"BXGY",      
      "originalCartAmount": (Int) | cart total value without any discounts,
      "discountAmount": (Int) | discount calculated,
      "finalCartAmount": (Int) | final cart amount after applying the discount,
      "message": (String) | customised message according to coupon type and discount amount
  }
}
```

## CASES NON IMPLEMENTED BUT THOUGHT OF -
- For BXGY type coupons, cart should be parsed for reward items and new reward items should be added if not present instead of just calculating discount based on existing reward items from the cart.
- In apply coupon, there is no modified cart shown. Individual product discounts are consolidated into the total discount displayed instead of being showed as individual product discounts in a modified cart.

## LIMITATIONS OF CURRENT SYSTEM (Future upgrades) -
- DB can be upgraded to persistent cloud based MySQL storage instead of in-memory storage which is prone to data loss in case of service restart.
- Cart logic not implemented or persisted.