(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CandidateJoborderController', CandidateJoborderController);

    CandidateJoborderController.$inject = ['$scope', '$state', 'CandidateJoborder'];

    function CandidateJoborderController ($scope, $state, CandidateJoborder) {
        var vm = this;

        vm.candidateJoborders = [];

        loadAll();

        function loadAll() {
            CandidateJoborder.query(function(result) {
                vm.candidateJoborders = result;
            });
        }
    }
})();
