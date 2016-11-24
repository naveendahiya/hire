(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('HttpLogDetailController', HttpLogDetailController);

    HttpLogDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'HttpLog'];

    function HttpLogDetailController($scope, $rootScope, $stateParams, previousState, entity, HttpLog) {
        var vm = this;

        vm.httpLog = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hireApp:httpLogUpdate', function(event, result) {
            vm.httpLog = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
