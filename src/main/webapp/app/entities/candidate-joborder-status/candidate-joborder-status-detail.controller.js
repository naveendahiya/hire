(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CandidateJoborderStatusDetailController', CandidateJoborderStatusDetailController);

    CandidateJoborderStatusDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CandidateJoborderStatus'];

    function CandidateJoborderStatusDetailController($scope, $rootScope, $stateParams, previousState, entity, CandidateJoborderStatus) {
        var vm = this;

        vm.candidateJoborderStatus = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hireApp:candidateJoborderStatusUpdate', function(event, result) {
            vm.candidateJoborderStatus = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
