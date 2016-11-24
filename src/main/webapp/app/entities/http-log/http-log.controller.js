(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('HttpLogController', HttpLogController);

    HttpLogController.$inject = ['$scope', '$state', 'HttpLog'];

    function HttpLogController ($scope, $state, HttpLog) {
        var vm = this;

        vm.httpLogs = [];

        loadAll();

        function loadAll() {
            HttpLog.query(function(result) {
                vm.httpLogs = result;
            });
        }
    }
})();
