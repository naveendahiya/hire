(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CandidateTagDetailController', CandidateTagDetailController);

    CandidateTagDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CandidateTag'];

    function CandidateTagDetailController($scope, $rootScope, $stateParams, previousState, entity, CandidateTag) {
        var vm = this;

        vm.candidateTag = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hireApp:candidateTagUpdate', function(event, result) {
            vm.candidateTag = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
