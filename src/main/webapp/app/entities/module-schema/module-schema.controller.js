(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('ModuleSchemaController', ModuleSchemaController);

    ModuleSchemaController.$inject = ['$scope', '$state', 'ModuleSchema'];

    function ModuleSchemaController ($scope, $state, ModuleSchema) {
        var vm = this;

        vm.moduleSchemas = [];

        loadAll();

        function loadAll() {
            ModuleSchema.query(function(result) {
                vm.moduleSchemas = result;
            });
        }
    }
})();
