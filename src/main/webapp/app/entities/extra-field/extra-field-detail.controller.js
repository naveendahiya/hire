(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('ExtraFieldDetailController', ExtraFieldDetailController);

    ExtraFieldDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ExtraField'];

    function ExtraFieldDetailController($scope, $rootScope, $stateParams, previousState, entity, ExtraField) {
        var vm = this;

        vm.extraField = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hireApp:extraFieldUpdate', function(event, result) {
            vm.extraField = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
