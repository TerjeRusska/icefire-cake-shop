$( document ).ready(function() {

    const loadOrderCakeData = function () {
        return $.get('/orders/cakes/all');
    };

    const getCakeNames = function (orderCakes) {
        const cakes = [];
        const cakeRows = $.map(orderCakes, function (orderCake) {
            if (!cakes.includes(orderCake.cake.name)) {
                cakes.push(orderCake.cake.name);
            }
        });
        return cakes;
    };

    const getCakeAmounts = function (orderCakes) {
        const amounts = [];
        const cakeRows = $.map(orderCakes, function (orderCake) {
            if (!cakes.includes(orderCake.cake.name)) {
                const value =
                cakes.push(orderCake.cake.name);
            }
        });
        return cakes;
    };

    const sortCakeData = function (orderCakes) {
        const dataArray = {};
        const cakeRows = $.map(orderCakes, function (orderCake) {
            if (!(orderCake.cake.name in dataArray)){
                dataArray[orderCake.cake.name] = orderCake.amount;
            } else {
                dataArray[orderCake.cake.name] = dataArray[orderCake.cake.name] + orderCake.amount;
            }
        });
        return dataArray;
    }

    const showStatistics = function (orderCakes){
        var ctx = document.getElementById("cakeChart").getContext('2d');
        const dataArray = sortCakeData(orderCakes);
        const data = Object.values(dataArray);
        const labels = Object.keys(dataArray);
        const generatedColours = data.map((value, index) => index % 2 ? '#343a40' : '#dc3545');

        var myChart = new Chart(ctx, {
            type: 'doughnut',
            data: {
                labels: labels,
                datasets: [{
                    data: data,
                    borderColor: generatedColours,
                    backgroundColor: generatedColours,
                }]
            },
            options: {
                title: {
                    display: true,
                    text: 'Total cakes ordered.'
                }
            }
        });
    }

    const refreshOrderCakeData = function () {
        loadOrderCakeData().then(function (orderCakes) {
            showStatistics(orderCakes);
        });
    };

    //when page is loaded
    refreshOrderCakeData();
});