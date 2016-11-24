(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('EeoVeteranTypeController', EeoVeteranTypeController);

    EeoVeteranTypeController.$inject = ['$scope', '$state', 'EeoVeteranType'];

    function EeoVeteranTypeController ($scope, $state, EeoVeteranType) {
        var vm = this;

        vm.eeoVeteranTypes = [];

        loadAll();

        function loadAll() {
            EeoVeteranType.query(function(result) {
                vm.eeoVeteranTypes = result;
            });
        }
    }
})();
