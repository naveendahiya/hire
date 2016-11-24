(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('EmailHistoryDetailController', EmailHistoryDetailController);

    EmailHistoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'EmailHistory'];

    function EmailHistoryDetailController($scope, $rootScope, $stateParams, previousState, entity, EmailHistory) {
        var vm = this;

        vm.emailHistory = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hireApp:emailHistoryUpdate', function(event, result) {
            vm.emailHistory = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
