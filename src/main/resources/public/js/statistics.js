$( document ).ready(function() {

    const loadOrderCakeData = function () {
        return $.get('/orders/cakes/all');
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

    const showStatisticsChart = function (orderCakes){
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
                responsive: true,
                legend: {
                  position: 'bottom',
                },
                title: {
                  display: true,
                  text: 'Percentage of each cake ordered'
                },
                animation: {
                  animateScale: true,
                  animateRotate: true
                },
                tooltips: {
                    callbacks: {
                        label: function(tooltipItem, data) {
                            var dataset = data.datasets[tooltipItem.datasetIndex];
                            var total = dataset.data.reduce(function(previousValue, currentValue, currentIndex, array) {
                                return previousValue + currentValue;
                            });
                            var currentValue = dataset.data[tooltipItem.index];
                            var currentLabel = data.labels[tooltipItem.index];
                            var precentage = Math.floor(((currentValue/total) * 100)+0.5);
                            return currentLabel + ": " + precentage + "%";
                        }
                    }
                }
            }
        });
    }

    const showStatisticsTable = function (orderCakes){
            var ctx = document.getElementById("cakeTable").getContext('2d');
            const dataArray = sortCakeData(orderCakes);
            const data = Object.values(dataArray);
            const labels = Object.keys(dataArray);
            const generatedColours = data.map((value, index) => index % 2 ? '#343a40' : '#dc3545');

            var myChart = new Chart(ctx, {
                type: 'horizontalBar',
                data: {
                    labels: labels,
                    datasets: [{
                        data: data,
                        borderColor: generatedColours,
                        backgroundColor: generatedColours,
                    }]
                },
                options: {
                    responsive: true,
                    legend: {
                        display: false
                    },
                    title: {
                      display: true,
                      text: 'Total amount of each cake ordered'
                    },
                    animation: {
                      animateScale: true,
                      animateRotate: true
                    }
                }
            });
        }

    const refreshOrderCakeData = function () {
        loadOrderCakeData().then(function (orderCakes) {
            showStatisticsChart(orderCakes);
            showStatisticsTable(orderCakes);
        });
    };

    //when page is loaded
    refreshOrderCakeData();
});