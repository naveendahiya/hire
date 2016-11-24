(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CandidateJobordrerStatusTypeDetailController', CandidateJobordrerStatusTypeDetailController);

    CandidateJobordrerStatusTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CandidateJobordrerStatusType'];

    function CandidateJobordrerStatusTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, CandidateJobordrerStatusType) {
        var vm = this;

        vm.candidateJobordrerStatusType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hireApp:candidateJobordrerStatusTypeUpdate', function(event, result) {
            vm.candidateJobordrerStatusType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
