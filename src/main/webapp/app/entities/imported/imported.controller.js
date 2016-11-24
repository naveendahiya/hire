(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('ImportedController', ImportedController);

    ImportedController.$inject = ['$scope', '$state', 'Imported'];

    function ImportedController ($scope, $state, Imported) {
        var vm = this;

        vm.importeds = [];

        loadAll();

        function loadAll() {
            Imported.query(function(result) {
                vm.importeds = result;
            });
        }
    }
})();
