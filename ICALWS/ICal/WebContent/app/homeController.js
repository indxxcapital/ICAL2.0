angular.module('app',[])
    .controller('homeController', ['$scope', function ($scope) {

    $scope.collapse = function (event) {
        $(event.target).toggleClass("glyphicon-chevron-down");
    };

    $scope.products = [{
                        "productid": 1001456,
                        "productname": "Spring Season Gift",
                        "amount": 250,
                        "orderDate": "2015-02-15T00:00:00Z",
                        "availablity":1,
                        "items": [
                                        {"prodDetailId": 17890,
                                            "prodDetailDesc": "PS 3",
                                            "amount": "150",
                                            "qty":1,
                                            "source":"EDI"
                                        },{"prodDetailId": 17891,
                                            "prodDetailDesc": "Heart shape Ring",
                                            "amount": "100",
                                            "qty": 1,
                                            "source":"FDS"
                                        },],
                        "manual": [
			                            {"prodDetailId": 17890,
			                                "prodDetailDesc": "",
			                                "amount": "150",
			                                "qty":1,
			                                "source":"EDI"
			                            }]
                         },{"productid": 1001457,
                             "productname": "Christmas Season Gift",
                             "amount": 349,
                             "orderDate": "2015-04-15T00:00:00Z",
                             "availablity": 1,
                             "items": [{
                                                 "prodDetailId": 17900,
                                                 "prodDetailDesc": "Choclate Giftbox",
                                                 "amount": "150",
                                                 "qty": 1
                                             },{
                                                 "prodDetailId": 17901,
                                                 "prodDetailDesc": "Xbox 360",
                                                 "amount": "199",
                                                 "qty": 1

                                             },]
                         },{
                             "productid": 1001458,
                             "productname": "Birthday Gift",
                             "availablity": "N/A",
                             "amount": 200
                         }];
 }]);