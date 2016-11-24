(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CandidateJoborderStatusHistoryDetailController', CandidateJoborderStatusHistoryDetailController);

    CandidateJoborderStatusHistoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CandidateJoborderStatusHistory'];

    function CandidateJoborderStatusHistoryDetailController($scope, $rootScope, $stateParams, previousState, entity, CandidateJoborderStatusHistory) {
        var vm = this;

        vm.candidateJoborderStatusHistory = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hireApp:candidateJoborderStatusHistoryUpdate', function(event, result) {
            vm.candidateJoborderStatusHistory = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
