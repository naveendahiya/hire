(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('AccessLevelController', AccessLevelController);

    AccessLevelController.$inject = ['$scope', '$state', 'AccessLevel'];

    function AccessLevelController ($scope, $state, AccessLevel) {
        var vm = this;

        vm.accessLevels = [];

        loadAll();

        function loadAll() {
            AccessLevel.query(function(result) {
                vm.accessLevels = result;
            });
        }
    }
})();
