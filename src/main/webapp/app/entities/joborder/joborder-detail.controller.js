(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('JoborderDetailController', JoborderDetailController);

    JoborderDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Joborder'];

    function JoborderDetailController($scope, $rootScope, $stateParams, previousState, entity, Joborder) {
        var vm = this;

        vm.joborder = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hireApp:joborderUpdate', function(event, result) {
            vm.joborder = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
