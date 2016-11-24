(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('ModuleSchemaDetailController', ModuleSchemaDetailController);

    ModuleSchemaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ModuleSchema'];

    function ModuleSchemaDetailController($scope, $rootScope, $stateParams, previousState, entity, ModuleSchema) {
        var vm = this;

        vm.moduleSchema = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hireApp:moduleSchemaUpdate', function(event, result) {
            vm.moduleSchema = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
