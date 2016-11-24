(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('ExtraFieldSettingsDetailController', ExtraFieldSettingsDetailController);

    ExtraFieldSettingsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ExtraFieldSettings'];

    function ExtraFieldSettingsDetailController($scope, $rootScope, $stateParams, previousState, entity, ExtraFieldSettings) {
        var vm = this;

        vm.extraFieldSettings = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hireApp:extraFieldSettingsUpdate', function(event, result) {
            vm.extraFieldSettings = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
