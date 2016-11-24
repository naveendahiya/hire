(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CandidateJoborderDetailController', CandidateJoborderDetailController);

    CandidateJoborderDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CandidateJoborder'];

    function CandidateJoborderDetailController($scope, $rootScope, $stateParams, previousState, entity, CandidateJoborder) {
        var vm = this;

        vm.candidateJoborder = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hireApp:candidateJoborderUpdate', function(event, result) {
            vm.candidateJoborder = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
