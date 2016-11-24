(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CandidateDetailController', CandidateDetailController);

    CandidateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Candidate'];

    function CandidateDetailController($scope, $rootScope, $stateParams, previousState, entity, Candidate) {
        var vm = this;

        vm.candidate = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hireApp:candidateUpdate', function(event, result) {
            vm.candidate = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
