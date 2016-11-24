(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('ImportedDetailController', ImportedDetailController);

    ImportedDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Imported'];

    function ImportedDetailController($scope, $rootScope, $stateParams, previousState, entity, Imported) {
        var vm = this;

        vm.imported = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hireApp:importedUpdate', function(event, result) {
            vm.imported = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
