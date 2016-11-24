(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('HttpLogTypesController', HttpLogTypesController);

    HttpLogTypesController.$inject = ['$scope', '$state', 'HttpLogTypes'];

    function HttpLogTypesController ($scope, $state, HttpLogTypes) {
        var vm = this;

        vm.httpLogTypes = [];

        loadAll();

        function loadAll() {
            HttpLogTypes.query(function(result) {
                vm.httpLogTypes = result;
            });
        }
    }
})();
