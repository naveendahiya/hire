(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('SavedSearchDetailController', SavedSearchDetailController);

    SavedSearchDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SavedSearch'];

    function SavedSearchDetailController($scope, $rootScope, $stateParams, previousState, entity, SavedSearch) {
        var vm = this;

        vm.savedSearch = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hireApp:savedSearchUpdate', function(event, result) {
            vm.savedSearch = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
