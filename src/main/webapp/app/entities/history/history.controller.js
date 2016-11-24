(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('HistoryController', HistoryController);

    HistoryController.$inject = ['$scope', '$state', 'History'];

    function HistoryController ($scope, $state, History) {
        var vm = this;

        vm.histories = [];

        loadAll();

        function loadAll() {
            History.query(function(result) {
                vm.histories = result;
            });
        }
    }
})();
