(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('SavedListDetailController', SavedListDetailController);

    SavedListDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SavedList'];

    function SavedListDetailController($scope, $rootScope, $stateParams, previousState, entity, SavedList) {
        var vm = this;

        vm.savedList = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hireApp:savedListUpdate', function(event, result) {
            vm.savedList = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
