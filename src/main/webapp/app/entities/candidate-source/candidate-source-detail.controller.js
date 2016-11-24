(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CandidateSourceDetailController', CandidateSourceDetailController);

    CandidateSourceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CandidateSource'];

    function CandidateSourceDetailController($scope, $rootScope, $stateParams, previousState, entity, CandidateSource) {
        var vm = this;

        vm.candidateSource = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hireApp:candidateSourceUpdate', function(event, result) {
            vm.candidateSource = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
