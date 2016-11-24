(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('EeoEthnicTypeController', EeoEthnicTypeController);

    EeoEthnicTypeController.$inject = ['$scope', '$state', 'EeoEthnicType'];

    function EeoEthnicTypeController ($scope, $state, EeoEthnicType) {
        var vm = this;

        vm.eeoEthnicTypes = [];

        loadAll();

        function loadAll() {
            EeoEthnicType.query(function(result) {
                vm.eeoEthnicTypes = result;
            });
        }
    }
})();
