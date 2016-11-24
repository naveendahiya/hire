(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('SavedListEntryDetailController', SavedListEntryDetailController);

    SavedListEntryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SavedListEntry'];

    function SavedListEntryDetailController($scope, $rootScope, $stateParams, previousState, entity, SavedListEntry) {
        var vm = this;

        vm.savedListEntry = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hireApp:savedListEntryUpdate', function(event, result) {
            vm.savedListEntry = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
