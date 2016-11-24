(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('MruController', MruController);

    MruController.$inject = ['$scope', '$state', 'Mru'];

    function MruController ($scope, $state, Mru) {
        var vm = this;

        vm.mrus = [];

        loadAll();

        function loadAll() {
            Mru.query(function(result) {
                vm.mrus = result;
            });
        }
    }
})();
